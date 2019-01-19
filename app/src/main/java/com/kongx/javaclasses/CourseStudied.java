package com.kongx.javaclasses;

/**
 * Created by DELL on 2017/2/17 0017.
 */

public class CourseStudied {
    //from input
    private String semester;
    private String name;
    private char classType;
    private String classId;
    private boolean isDoubleCourse;
    private float score;
    private float credit;
    //from inner calculation
    private float creditCalculated;             //防止有“通过”的情况
    private float[] gpas;
    private boolean isDivider;

    public CourseStudied(DoubleCourseMark doubleCourseMark) {
        switch (doubleCourseMark){
            case MAJORCOURSE:
                isDoubleCourse = false;
                break;
            case MINORCOURSE:
                isDoubleCourse = true;
                break;
        }
        isDivider = false;
    }       //for normal courses;

    public CourseStudied(char dividerType) {        //for Divider Initialization
        isDivider = true;
        classType = dividerType;
    }       //for dividers

    public void setIsDoubleCourse(boolean input) {
        isDoubleCourse = input;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        try {
            this.semester = semester.substring(0,9)+"年第"+semester.charAt(semester.length() - 1) + "学期";
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getClassType() {
        return classType;
    }

    public void setClassType(String type) {
        if (type.contains("任选课") || type.contains("通识选修")) {
            classType = 'E';
        } else if (type.contains("专业") && type.contains("选修")) {
            classType = 'D';
        } else if (type.contains("专业") && type.contains("必修")) {
            classType = 'C';
        } else if (type.contains("院系") && type.contains("必修")) {
            classType = 'B';
        } else {
            classType = 'A';
        }
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String input) {
        classId = input;
    }

    public boolean isDoubleCourse() {
        return isDoubleCourse;
    }

    public float getScore() {
        return score;
    }

    public void setScore(String score) {
        try {
            if(score.charAt(0) >= '0' && score.charAt(0) <= '9'){       //课程有分数
                creditCalculated = credit;
                this.score = Float.parseFloat(score);
                calculateGPA();
            }else{              //课程为 通过
                this.score = 60;
                creditCalculated = 0;
                gpas = new float[5];
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(String input) {
        try {
            credit = Float.parseFloat(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getCreditCalculated() {
        return creditCalculated;
    }

    public float[] getGpas() {
        return gpas;
    }

    public boolean isDivider() {
        return isDivider;
    }

    private void calculateGPA(){
        gpas = new float[5];
        //Standart GPA
        if(score >= 90) gpas[0] = 4.0f;
        else if(score >= 80)    gpas[0] = 3.0f;
        else if(score >= 70)    gpas[0] = 2.0f;
        else if(score >= 60)    gpas[0] = 1.0f;
        else gpas[0] = 0f;
        //Modified GPA(1)
        if(score >= 85) gpas[1] = 4.0f;
        else if(score >= 70)    gpas[1] = 3.0f;
        else if(score >= 60)    gpas[1] = 2.0f;
        else gpas[1] = 0f;
        //Modified GPA(2)
        if(score >= 85) gpas[2] = 4.0f;
        else if(score >= 75)    gpas[2] = 3.0f;
        else if(score >= 60)    gpas[2] = 2.0f;
        else gpas[2] = 0f;
        //PKU GPA
        if(score >= 90) gpas[3] = 4.0f;
        else if(score >= 85)    gpas[3] = 3.7f;
        else if(score >= 82)    gpas[3] = 3.3f;
        else if(score >= 78)    gpas[3] = 3.0f;
        else if(score >= 75)    gpas[3] = 2.7f;
        else if(score >= 72)    gpas[3] = 2.3f;
        else if(score >= 68)    gpas[3] = 2.0f;
        else if(score >= 64)    gpas[3] = 1.5f;
        else if(score >= 60)    gpas[3] = 1.0f;
        else gpas[3] = 0f;
        //CANADIAN GPA
        if(score >= 90) gpas[4] = 4.3f;
        else if(score >= 85)    gpas[4] = 4.0f;
        else if(score >= 80)    gpas[4] = 3.7f;
        else if(score >= 75)    gpas[4] = 3.3f;
        else if(score >= 70)    gpas[4] = 3.0f;
        else if(score >= 65)    gpas[4] = 2.7f;
        else if(score >= 60)    gpas[4] = 2.3f;
        else gpas[4] = 0f;
    }

    public enum DoubleCourseMark {MAJORCOURSE, MINORCOURSE}
}
