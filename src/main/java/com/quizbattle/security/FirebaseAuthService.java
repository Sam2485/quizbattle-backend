package com.quizbattle.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    public FirebaseUser verifyToken(String idToken) {

        try {

            FirebaseToken decodedToken =
                    FirebaseAuth.getInstance().verifyIdToken(idToken);

            FirebaseUser user = new FirebaseUser(
                    decodedToken.getUid(),
                    decodedToken.getEmail(),
                    decodedToken.getName()
            );

            return user;

        } catch (Exception e) {

            throw new RuntimeException("Invalid Firebase token", e);

        }
    }

}