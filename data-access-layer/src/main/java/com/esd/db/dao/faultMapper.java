package com.esd.db.dao;

import com.esd.db.model.fault;

public interface faultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int deleteByPrimaryKey(Integer faultId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int insert(fault record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int insertSelective(fault record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    fault selectByPrimaryKey(Integer faultId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int updateByPrimaryKeySelective(fault record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int updateByPrimaryKeyWithBLOBs(fault record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fault
     *
     * @mbggenerated Mon Aug 25 13:54:58 CST 2014
     */
    int updateByPrimaryKey(fault record);
}