package hr.algebra.hostandtravel.repository;

import hr.algebra.hostandtravel.domain.Person;
import hr.algebra.hostandtravel.domain.Review;
import hr.algebra.hostandtravel.domain.rowmapper.PersonRowMapper;
import hr.algebra.hostandtravel.domain.rowmapper.ReviewRowMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
@Primary
@EnableAutoConfiguration
public class ReviewRepository implements Repository<Review>{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate ;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);


        this.inserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Review")
                .usingGeneratedKeyColumns("IDReview");
    }

    @Override
    public List<Review> getAllEntities() {
        return null;
    }

    @Override
    public Review getEntity(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource("idReview", id);

        List<Review> reviews = namedParameterJdbcTemplate.query("SELECT * FROM Review WHERE IDReview =:idReview", parameters,new ReviewRowMapper());
        if(!reviews.isEmpty()){
            return reviews.get(0);
        }
        return null;
    }

    @Override
    public Boolean updateEntity(Review entity) {
        return null;
    }

    @Override
    public Review insertEntity(Review review) {
        Map<String, Object> params = new HashMap<>();
        params.put("Rating",review.getRating());
        params.put("Description", review.getDescription());
        params.put("ReviewDate", review.getReviewDate());


        int key = inserter.executeAndReturnKey(params).intValue();
        review.setIdReview(key);

        return review;
    }

    @Override
    public Boolean deleteEntity(int id) {
        return null;
    }
}
