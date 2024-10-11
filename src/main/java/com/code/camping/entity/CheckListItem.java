package com.code.camping.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "checklistitem")
public class CheckListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "checklist_id", nullable = false)
    private CheckList checklist;

}
