package fr.vulture.hostocars.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entity for the {@code DatabaseInfo} table.
 */
@Entity
@Data
@Table(name = "DatabaseInfo")
public class Property implements Serializable {

    private static final long serialVersionUID = 5273678088222310366L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
        unique = true,
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "key",
        unique = true,
        nullable = false,
        columnDefinition = "TEXT")
    private String key;

    @Column(name = "value",
        columnDefinition = "TEXT")
    private String value;

}
