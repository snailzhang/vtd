package com.esd.db.dao;

import com.esd.db.model.District;

public interface districtMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    int insert(District record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    int insertSelective(District record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    District selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    int updateByPrimaryKeySelective(District record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table district
     *
     * @mbggenerated Tue Oct 14 11:26:21 CST 2014
     */
    int updateByPrimaryKey(District record);
}