package com.kh.product.domain.product.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor

public class ProductDAOImpl implements ProductDAO {

  private final JdbcTemplate jdbcTemplate;
  
  //등록
  @Override
  public Product create(Product product) {
    // SQL 작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product (product_id,name,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, ?,?,?) ");

    // SQL 실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"product_id"} //insert 후 insert 레코드 중 반환할 컬럼명, keyHolder에 저장됨.
        );

        pstmt.setString(1, product.getName());
        pstmt.setLong(2, product.getQuantity());
        pstmt.setLong(3, product.getPrice());

        return pstmt;
      }
    },keyHolder);


    long product_id = Long.valueOf(keyHolder.getKeys().get("product_id").toString());
    return selectOne(product_id);
  }
  
  //조회
  @Override
  public Product selectOne(Long productId) {
    StringBuffer sql = new StringBuffer();

    sql.append(" SELECT ");
    sql.append("     product_id, ");
    sql.append("     name, ");
    sql.append("     quantity, ");
    sql.append("     price ");
    sql.append(" FROM ");
    sql.append("     product ");
    sql.append(" where product_id = ? ");

    Product ProductItem = null;
    try {
      ProductItem = jdbcTemplate.queryForObject(
          sql.toString(),
          new BeanPropertyRowMapper<>(Product.class),
          productId);
    } catch (Exception e) { //1건을 못찾으면
      ProductItem = null;
    }

    return ProductItem;
  }
  
  //수정
  @Override
  public void update(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();

    sql.append(" update product ");
    sql.append(" set name = ?, ");
    sql.append("     quantity = ?, ");
    sql.append("     price = ? ");
    sql.append(" where product_id = ? ");

  jdbcTemplate.update(
        sql.toString(),
        product.getName(),
        product.getQuantity(),
        product.getPrice(),
        productId
    );

  }

  //삭제
  @Override
  public Product delete(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM product ");
    sql.append(" WHERE  product_id = ? ");

     jdbcTemplate.update(sql.toString(), productId);


    return null;
  }
  
  //전체조회
  @Override
  public List<Product> selectAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select ");
    sql.append("       product_id, ");
    sql.append("       name, ");
    sql.append("       quantity, ");
    sql.append("       price ");
    sql.append("  from product ");

    List<Product> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Product.class)
    );

    return list;
  }
}
