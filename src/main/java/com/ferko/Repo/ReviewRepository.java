package com.ferko.Repo;

import com.ferko.Models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.ui.Model;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
