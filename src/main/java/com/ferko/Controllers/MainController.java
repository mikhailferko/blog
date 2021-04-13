package com.ferko.Controllers;

import com.ferko.Models.Review;
import com.ferko.Models.Role;
import com.ferko.Models.User;
import com.ferko.Repo.ReviewRepository;
import com.ferko.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Misha");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Страница о нас");
        return "about";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("title", "Страница с отзывами");
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/reviews-add")
    public String reviewsAdd(Model model) {
        model.addAttribute("title", "Ваш отзыв");
        return "reviews-add";
    }

    @PostMapping("/reviews-add/add")
    public String reviewsControl(@RequestParam String title, @RequestParam String text, Model model) {
        Review review = new Review(title, text);
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/reviews/{id}")
    public String reviewsInfo(@PathVariable(value = "id") long id, Model model) {
        Optional<Review> reviews = reviewRepository.findById(id);
        ArrayList<Review> result = new ArrayList<>();
        reviews.ifPresent(result::add);
        model.addAttribute("title", "Отзыв");
        model.addAttribute("review", result);
        return "review-info";
    }

    @GetMapping("/reviews/{id}/update")
    public String reviewsUpdate(@PathVariable(value = "id") long id, Model model) {
        Optional<Review> reviews = reviewRepository.findById(id);
        ArrayList<Review> result = new ArrayList<>();
        reviews.ifPresent(result::add);
        model.addAttribute("title", "Редактирование отзыва");
        model.addAttribute("review", result);
        return "review-update";
    }

    @PostMapping("/reviews/{id}/delete")
    public String reviewsDelete(@PathVariable(value = "id") long id, Model model) throws ClassNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ClassNotFoundException());
        reviewRepository.delete(review);
        return "redirect:/reviews";
    }

    @PostMapping("/reviews/{id}/update")
    public String reviewUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String text, Model model) throws ClassNotFoundException{
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ClassNotFoundException());
        review.setTitle(title);
        review.setText(text);
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String regInfo(User user, Model model) {
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

}