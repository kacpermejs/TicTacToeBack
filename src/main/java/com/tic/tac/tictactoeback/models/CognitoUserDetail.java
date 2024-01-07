package com.tic.tac.tictactoeback.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cognito_to_user_details")
public class CognitoUserDetail {
    @Id
    @Column(nullable = false)
    private String cognitoUserId;
    
    @Column(nullable = false, unique = true)
    private Long userDetailId;

}
