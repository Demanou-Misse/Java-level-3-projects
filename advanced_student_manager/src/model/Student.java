package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private Map<String, Grade> gradeMap;

    public Student (String name, Map<String, Grade> gradeMap) {
        this.name = name;
        this.gradeMap = gradeMap;
    }

    public String getName() {
        return name;
    }

    public Map<String, Grade> getGradeMap() {
        return gradeMap;
    }

    public void average () {
        if (gradeMap.isEmpty()) System.out.println("❌ No grade found.");
        else {
            double sum = 0;
            for (Map.Entry<String, Grade> entry : gradeMap.entrySet()) {
                Grade grade = entry.getValue();
                sum += grade.getValue();
            }
            double average = sum / gradeMap.size();
            System.out.printf("📊 Average for %s: %.2f", name, average);
            System.out.println(" ");
        }
    }

}
