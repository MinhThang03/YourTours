package com.hcmute.yourtours.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "province")
public class Province implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code_name", unique = true)
    private String codeName;

    @Column(name = "division_type")
    private String divisionType;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "en_name")
    private String enName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    @Fetch(FetchMode.SUBSELECT)
    private List<Homes> homes;
}
