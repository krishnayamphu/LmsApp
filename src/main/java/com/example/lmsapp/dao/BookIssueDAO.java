package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.BookIssue;
import com.example.lmsapp.models.User;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookIssueDAO {
    public static List<BookIssue> findAll() throws SQLException {
        List<BookIssue> issues = new ArrayList<>();
        String sql = "SELECT bi.*, b.title AS book_title, m.name AS member_name FROM book_issue bi JOIN books b ON b.id = bi.book_id JOIN members m ON m.id = bi.member_id ORDER BY bi.id DESC";
        try (Connection con = ConnectDB.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookIssue issue = new BookIssue();
                issue.setId(rs.getInt("id"));
                issue.setBookId(rs.getInt("book_id"));
                issue.setMemberId(rs.getInt("member_id"));
                issue.setBookTitle(rs.getString("book_title"));
                issue.setMemberName(rs.getString("member_name"));
                issue.setIssuedBy(rs.getInt("issued_by"));
                issue.setReturnBy(rs.getInt("return_by"));
                issue.setIssueAt(rs.getString("issue_at"));
                issue.setDueDate(rs.getDate("due_date"));
                issue.setReturnAt(rs.getString("return_at"));
                issue.setNotes(rs.getString("notes"));
                issue.setFine(rs.getDouble("fine"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getString("created_at"));
                issue.setUpdatedAt(rs.getString("updated_at"));
                issues.add(issue);
            }
        }

        return issues;
    }

    public static BookIssue findById(int id) throws SQLException {
        String sql = "SELECT bi.*, b.title AS book_title, m.name AS member_name FROM book_issue bi JOIN books b ON b.id = bi.book_id JOIN members m ON m.id = bi.member_id WHERE bi.id = ?";
        try (Connection con = ConnectDB.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                BookIssue issue = new BookIssue();
                issue.setId(rs.getInt("id"));
                issue.setBookId(rs.getInt("book_id"));
                issue.setMemberId(rs.getInt("member_id"));
                issue.setIssuedBy(rs.getInt("issued_by"));
                issue.setReturnBy(rs.getInt("return_by"));
                issue.setIssueAt(rs.getString("issue_at"));
                issue.setDueDate(rs.getDate("due_date"));
                issue.setReturnAt(rs.getString("return_at"));
                issue.setNotes(rs.getString("notes"));
                issue.setFine(rs.getDouble("fine"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getString("created_at"));
                issue.setUpdatedAt(rs.getString("updated_at"));
                return issue;
            }
        }
    }

    public static List<BookIssue> findByMemberId(int memberId) throws SQLException {
        List<BookIssue> issues = new ArrayList<>();
        String sql = "SELECT bi.*, b.title AS book_title FROM book_issue bi JOIN books b ON b.id = bi.book_id WHERE bi.member_id = ? ORDER BY bi.id DESC";
        try (Connection con = ConnectDB.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookIssue issue = new BookIssue();
                    issue.setId(rs.getInt("id"));
                    issue.setBookTitle(rs.getString("book_title"));
                    issue.setIssueAt(rs.getString("issue_at"));
                    Date dueDate = rs.getDate("due_date");
                    issue.setDueDate(dueDate);
                    issue.setReturnAt(rs.getString("return_at"));
                    issue.setFine(rs.getDouble("fine"));
                    issue.setStatus(rs.getString("status"));
                    issues.add(issue);
                }
            }
        }

        return issues;
    }

    public static void issueBook(int bookId, int memberId, Date issueDate, Date dueDate, String notes, User user) throws SQLException {
        if (issueDate == null || dueDate == null || dueDate.before(issueDate)) {
            throw new IllegalArgumentException("Enter valid issue and due dates.");
        }
        try (Connection con = ConnectDB.connect()) {
            con.setAutoCommit(false);
            try {
                int available;
                try (PreparedStatement ps = con.prepareStatement("SELECT available_quantity FROM books WHERE id = ? FOR UPDATE")) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Book not found.");
                        }
                        available = rs.getInt("available_quantity");
                    }
                }

                if (available <= 0) {
                    throw new SQLException("No copies of this book are currently available.");
                }
                String sql = "INSERT INTO book_issue (book_id,member_id,issued_by,issue_at,due_date,notes,status) VALUES (?,?,?,?,?,?,'ISSUED')";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.setInt(3, user.getId());
                    ps.setDate(4, issueDate);
                    ps.setDate(5, dueDate);
                    ps.setString(6, notes);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement("UPDATE books SET available_quantity = available_quantity - 1 WHERE id = ?")) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();

            } catch (SQLException | RuntimeException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public static void updateDueDate(int issueId, Date dueDate) throws SQLException {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date is required.");
        }

        String sql = "UPDATE book_issue SET due_date = ? WHERE id = ? AND status = 'ISSUED' AND issue_at <= ?";

        try (Connection con = ConnectDB.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, dueDate);
            ps.setInt(2, issueId);
            ps.setDate(3, dueDate);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Issue not found, already returned, or invalid due date.");
            }
        }
    }

    public static void returnBook(int issueId) throws SQLException {
        try (Connection con = ConnectDB.connect()) {
            con.setAutoCommit(false);
            try {
                int bookId;
                String sql = "SELECT book_id FROM book_issue WHERE id = ? AND status = 'ISSUED' FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, issueId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Active issue not found or already returned.");
                        }
                        bookId = rs.getInt("book_id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement("UPDATE book_issue SET status = 'RETURNED', return_at = CURRENT_DATE WHERE id = ?")) {
                    ps.setInt(1, issueId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement("UPDATE books SET available_quantity = available_quantity + 1 WHERE id = ?")) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }
                con.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    con.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    public static void deleteReturned(int issueId) throws SQLException {
        String sql = "DELETE FROM book_issue WHERE id = ? AND status = 'RETURNED'";

        try (Connection con = ConnectDB.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, issueId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Record not found or the book has not been returned.");
            }
        }
    }
}

