package com.esd.db.dao;

import com.esd.db.model.pay;

public interface payMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    int insert(pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    int insertSelective(pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    pay selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    int updateByPrimaryKeySelective(pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay
     *
     * @mbggenerated Tue Feb 03 11:05:10 CST 2015
     */
    int updateByPrimaryKey(pay record);
}