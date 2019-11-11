https://www.jianshu.com/p/700452aaae8c

   <select id="newShowTaskDataList" resultMap="baseResultMap">
       select JSON_UNQUOTE(data.custom2->'$.lastDeptName') as custom2, data.*  from (select t.data_id,t.encrypt_mobile_no,t.mobile_no,t.customer_name,t.province,t.city,t.data_status,t.handle_user,
			t.department,t.batch,t.import_time,t.update_time,'' as is_conn_last,'' as call_num,t.task_id,t.data_source,t.special_no,
			'' as forecast,t.version,t.sub_org,t.grid_org,t.block_org,t.tenant_id,'' as obs_log_id,t.custom1,t.custom2,
			<choose>
             <when test="isHide==0 or isHide=='' or isHide==null">
                 mobile_no
             </when>
             <otherwise>
                 encrypt_mobile_no
             </otherwise>
             </choose>
         as mobile
         from tbl_data_target_task_${tenantAlias} t where t.task_id=#{taskId} and t.tenant_id=#{tenantId}
