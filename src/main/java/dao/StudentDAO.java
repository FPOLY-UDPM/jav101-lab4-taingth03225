package dao;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class StudentDAO {
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
//Câu lệnh sau tùy thuộc vào bảng dữ liệu trong database
//Truy vấn database lấy id, masv, hoten
// HOẶC
        String sql = "SELECT id, masv, Ho + ' ' + TenLot + ' ' + Ten as hoten FROM SINHVIEN";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("masv"),
                        rs.getString("hoten")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Student findById(int id) {
        String sql = "SELECT id, masv, Ho + ' ' + TenLot + ' ' + Ten as hoten FROM SINHVIEN WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("masv"),
                        rs.getString("hoten")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] splitName(String fullName) {
        String[] result = {"", "", ""};
        if (fullName == null || fullName.trim().isEmpty()) return result;
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) {
            result[0] = parts[0];
        } else if (parts.length == 2) {
            result[0] = parts[0];
            result[2] = parts[1];
        } else {
            result[0] = parts[0];
            result[2] = parts[parts.length - 1];
            StringBuilder mid = new StringBuilder();
            for (int i = 1; i < parts.length - 1; i++) {
                mid.append(parts[i]).append(i == parts.length - 2 ? "" : " ");
            }
            result[1] = mid.toString();
        }
        return result;
    }

    public void insert(Student s) {
        String sql = "INSERT INTO SINHVIEN(masv, Ho, TenLot, Ten) VALUES (?, ?, ?, ?)";
        String[] names = splitName(s.getHoten());
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getMasv());
            ps.setString(2, names[0]);
            ps.setString(3, names[1]);
            ps.setString(4, names[2]);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Student s) {
        String sql = "UPDATE SINHVIEN SET masv=?, Ho=?, TenLot=?, Ten=? WHERE id=?";
        String[] names = splitName(s.getHoten());
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getMasv());
            ps.setString(2, names[0]);
            ps.setString(3, names[1]);
            ps.setString(4, names[2]);
            ps.setInt(5, s.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM SINHVIEN WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


