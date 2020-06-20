package fr.vulture.hostocars.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entity for the {@code DatabaseInfo} table.
 */
@Entity
@Data
@Table(name = "DatabaseInfo")
public class PropertyEntity implements Serializable {

    private static final long serialVersionUID = 3555420010453885846L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false, columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "key", unique = true, nullable = false, columnDefinition = "TEXT")
    private String key;

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

}
