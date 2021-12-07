package com.andante.swith.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Follow {

    @Id @GeneratedValue
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    @NotNull
    @Column(nullable = false)
    private Timestamp createdDate;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer approve;

    public void updateApprove() {
        this.approve = 1;
    }
}
