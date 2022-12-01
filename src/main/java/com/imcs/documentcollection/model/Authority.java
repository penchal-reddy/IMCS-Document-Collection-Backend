package com.imcs.documentcollection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Authority {
    @Id
    @Column(name = "username")
    @JsonProperty("username")
    private String username;

    @Column(name = "authority")
    @JsonProperty("authority")
    private String authority;

}
