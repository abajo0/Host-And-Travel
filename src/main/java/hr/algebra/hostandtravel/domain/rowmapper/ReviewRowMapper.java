package hr.algebra.hostandtravel.domain.rowmapper;

import hr.algebra.hostandtravel.domain.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Review(
                rs.getInt("IDReview"),
                rs.getInt("Rating"),
                rs.getString("Description"),
                rs.getDate("ReviewDate").toLocalDate());
    }
}
