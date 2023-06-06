package kz.bitlab.task41.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Task {
     private Long id;
     String name;
     Date deadlineDate;
     String description;
     boolean isCompleted;
}
