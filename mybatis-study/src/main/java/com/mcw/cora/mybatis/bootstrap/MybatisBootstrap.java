package com.mcw.cora.mybatis.bootstrap;

import com.mcw.cora.mybatis.entity.Course;
import com.mcw.cora.mybatis.entity.StSc;
import com.mcw.cora.mybatis.entity.Student;
import com.mcw.cora.mybatis.mapper.CourseMapper;
import com.mcw.cora.mybatis.mapper.StScMapper;
import com.mcw.cora.mybatis.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Random;

/**
 * @Author yibi
 * Date 2019/5/28 22:04
 * Version 1.0
 **/
public class MybatisBootstrap {

    private static Random random = new Random();

    public static void main(String[] args) {
        insertCourse();
        //insertStudent();
        //insertStSc();
    }

    public static void insertStudent() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream ins = MybatisBootstrap.class.getClassLoader().getResourceAsStream("configuration.xml");
        SqlSessionFactory sessionFactory = factoryBuilder.build(ins);
        SqlSession sqlSession = sessionFactory.openSession();

        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        for (int i = 0; i < 7000; i++) {
            studentMapper.insert(new Student("张也" + i, 21));
            studentMapper.insert(new Student("王新竹" + i, 18));
            studentMapper.insert(new Student("徐熙颜" + i, 20));
            studentMapper.insert(new Student("刘国雄" + i, 19));
            studentMapper.insert(new Student("汤潮" + i, 17));
            studentMapper.insert(new Student("张治中" + i, 18));
            studentMapper.insert(new Student("黄洋达" + i, 22));
            studentMapper.insert(new Student("田长青" + i, 16));
            studentMapper.insert(new Student("刘艮山" + i, 21));
            studentMapper.insert(new Student("张大山" + i, 18));
            if (i % 30 == 0) {
                sqlSession.commit();
            }
        }

        sqlSession.commit();

        sqlSession.close();
    }

    public static void insertCourse() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream ins = MybatisBootstrap.class.getClassLoader().getResourceAsStream("configuration.xml");
        SqlSessionFactory sessionFactory = factoryBuilder.build(ins);
        SqlSession sqlSession = sessionFactory.openSession();

        CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
        for (int i = 0; i < 1; i++) {
            courseMapper.insert(new Course("英语" + i));
        }
        sqlSession.commit();
        sqlSession.close();

    }

    public static void insertStSc() {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream ins = MybatisBootstrap.class.getClassLoader().getResourceAsStream("configuration.xml");
        SqlSessionFactory sessionFactory = factoryBuilder.build(ins);
        SqlSession sqlSession = sessionFactory.openSession();

        StScMapper stScMapper = sqlSession.getMapper(StScMapper.class);
        for (int i = 0; i < 700000; i++) {
            int courseId = i % 100;
            int studentId = i % 70000;
            int score = random.nextInt(50) + 50;
            stScMapper.insert(new StSc(studentId, courseId, score));
            if (i % 30 == 0) {
                sqlSession.commit();
            }
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
