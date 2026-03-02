package com.quizbattle.rating.service;

import com.quizbattle.rating.entity.Rating;
import com.quizbattle.rating.repository.RatingRepository;
import com.quizbattle.subject.entity.Subject;
import com.quizbattle.user.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



@Service
public class RatingService {

    private final RatingRepository ratingRepository;


    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }



    @Transactional
    public Rating getOrCreateRating(User user, Subject subject) {

        Optional<Rating> existing =
                ratingRepository.findByUserAndSubject(user, subject);

        if (existing.isPresent()) {
            return existing.get();
        }

        Rating rating = new Rating(user, subject);

        return ratingRepository.save(rating);
    }



    public int calculateNewRating(
            int playerRating,
            int opponentRating,
            boolean playerWon,
            int matchesPlayed
    ) {

        double expected =
                1.0 /
                        (1.0 +
                                Math.pow(
                                        10,
                                        (opponentRating - playerRating) / 400.0
                                )
                        );


        int actual = playerWon ? 1 : 0;


        int k = getKFactor(playerRating, matchesPlayed);


        return (int) Math.round(
                playerRating + k * (actual - expected)
        );

    }



    private int getKFactor(int rating, int matchesPlayed) {

        if (matchesPlayed < 30)
            return 40;

        if (rating < 1600)
            return 32;

        if (rating < 2000)
            return 24;

        return 16;
    }



    @Transactional
    public void updateRatings(
            Rating player,
            Rating opponent,
            boolean playerWon
    ) {

        int playerNewRating =
                calculateNewRating(
                        player.getRating(),
                        opponent.getRating(),
                        playerWon,
                        player.getMatchesPlayed()
                );


        int opponentNewRating =
                calculateNewRating(
                        opponent.getRating(),
                        player.getRating(),
                        !playerWon,
                        opponent.getMatchesPlayed()
                );


        player.setRating(playerNewRating);
        opponent.setRating(opponentNewRating);


        player.setMatchesPlayed(player.getMatchesPlayed() + 1);
        opponent.setMatchesPlayed(opponent.getMatchesPlayed() + 1);


        if (playerWon) {

            player.setWins(player.getWins() + 1);

            opponent.setLosses(opponent.getLosses() + 1);

        } else {

            player.setLosses(player.getLosses() + 1);

            opponent.setWins(opponent.getWins() + 1);

        }


        ratingRepository.save(player);

        ratingRepository.save(opponent);

    }

}