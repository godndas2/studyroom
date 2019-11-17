package org.halfdev.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AUTHORITY")
@Data
public class Authority {

    @Id
    @Column(name = "NAME", length = 50)
    @NotNull
    private String name;
}
