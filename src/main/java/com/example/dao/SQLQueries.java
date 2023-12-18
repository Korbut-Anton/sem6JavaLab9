package com.example.dao;

public class SQLQueries {
  static final String CREATE_FUNCS_QUERY = """
          CREATE OR REPLACE FUNCTION get_session_results
          (OUT failed integer, OUT passed integer, OUT excellent integer)
          RETURNS RECORD
          LANGUAGE plpgsql AS
          $func$
          DECLARE temprow RECORD;
          BEGIN
            failed := 0;
            passed := 0;
            excellent := 0;
            FOR temprow IN SELECT MIN(grade) AS min_grade FROM session_grades
            GROUP BY student_id
            LOOP
              IF temprow.min_grade < 4 THEN
                failed := failed + 1;
              ELSE
                passed := passed + 1;
                IF temprow.min_grade >= 9 THEN
                  excellent := excellent + 1;
                END IF;
              END IF;
            END LOOP;
          END $func$;
          
          CREATE OR REPLACE FUNCTION remove_student
          (surname_param TEXT, name_param TEXT, group_param integer)
          RETURNS bool
          LANGUAGE plpgsql AS
          $func$
          DECLARE row_with_id RECORD;
          BEGIN
            FOR row_with_id IN SELECT student_id FROM students
            WHERE(surname = surname_param AND "name" = name_param
            AND "group" = group_param)
            LOOP -- only one iteration, just to get record from view datatype
          	  DELETE FROM session_grades
          	  WHERE student_id = row_with_id.student_id;
          	  DELETE FROM students
          	  WHERE student_id = row_with_id.student_id;
          	  RETURN true;
            END LOOP;
            RETURN false;
          END $func$;""";
  static final String GET_LIST_OF_SUBJECTS_QUERY = """
          SELECT subject_name FROM subjects
          ORDER BY subject_id;""";
  static final String GET_LIST_OF_STUDENTS_QUERY = """
          SELECT surname, "name", "group" FROM students
          ORDER BY surname, "name", "group";""";
  static final String INSERT_NEW_STUDENT_QUERY = """
          INSERT INTO students (surname, "name", "group")
          VALUES
          (?, ?, ?) RETURNING student_id""";
  static final String CALL_FUNC_QUERY = "SELECT get_session_results();";

  static String removeStudentQuery(String surname, String name, String group) {
    return "SELECT remove_student(TEXT '" + surname + "', TEXT '" +
            name + "', " + group + ");";
  }

  static String insertStudentsGradesQuery(String gradesInArrFormat,
                                          long studentId) {
    return """
            DO $$
            DECLARE
            grades smallint[] := ARRAY""" + gradesInArrFormat + ";\n" +
            """
            temprow RECORD;
            cnt integer := 1;
            BEGIN
              FOR temprow IN SELECT subject_id FROM subjects
              ORDER BY subject_id
              LOOP
              INSERT INTO session_grades (student_id, subject_id, grade)
              VALUES (""" + studentId + ", temprow.subject_id, " +
            "grades[cnt]);\n" +
            """
              cnt := cnt + 1;
              END LOOP;
            END$$;""";
  }
}

