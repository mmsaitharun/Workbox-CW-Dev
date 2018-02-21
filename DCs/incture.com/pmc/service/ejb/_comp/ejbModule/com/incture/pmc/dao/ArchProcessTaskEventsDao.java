package com.incture.pmc.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.incture.pmc.dto.ProcessEventsDto;
import com.incture.pmc.entity.ProcessEventsDo;
import com.incture.pmc.util.ExecutionFault;
import com.incture.pmc.util.InvalidInputFault;
import com.incture.pmc.util.NoResultFault;
import com.incture.pmc.util.PMCConstant;
import com.incture.pmc.util.ServicesUtil;

/**
 * @author Saurabh
 *
 */
public class ArchProcessTaskEventsDao extends BaseDao<ProcessEventsDo, ProcessEventsDto> {
	public ArchProcessTaskEventsDao(EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * @param Integer
	 *            past noOfDays to archive data
	 */
	public void archiveData(int noOfDays) {
		String query = "";
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss a");
		Query q = null;
		Object resultList = null;
		try {

			/**
			 * Copying ProcessEvents Data to Arch_ProcessEvents
			 */

			query = "insert into ARCH_PROCESS_EVENTS select * from PROCESS_EVENTS p where p.STATUS = '" + PMCConstant.TASK_COMPLETED + "' and p.COMPLETED_AT < '"
					+ dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "'";
			System.err.println("get - " + query);
			q = this.getEntityManager().createNativeQuery(query.trim());
			resultList = q.executeUpdate();
			System.err.println("PROCESS_EVENTS archived Result : " + resultList.getClass() + " Result : " + resultList.toString());

			/**
			 * Copying TaskEvents Data to Arch_TaskEvents
			 */
			query = "insert into ARCH_TASK_EVENTS select t.* from TASK_EVENTS t,PROCESS_EVENTS p  where p.PROCESS_ID = t.PROCESS_ID and  p.STATUS = '" + PMCConstant.TASK_COMPLETED
					+ "' and p.COMPLETED_AT < '" + dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "'";
			System.err.println("get - " + query);
			q = this.getEntityManager().createNativeQuery(query.trim());
			resultList = q.executeUpdate();
			System.err.println("TASK_EVENTS archived reuld : " + resultList.getClass() + " Result : " + resultList.toString());

			/**
			 * Copying TaskOwner Data to Arch_TaskOwner
			 */
			query = "insert into ARCH_TASK_OWNERS select o.* from TASK_OWNERS o, TASK_EVENTS t,PROCESS_EVENTS p  where p.PROCESS_ID = t.PROCESS_ID and t.EVENT_ID = o.EVENT_ID and  p.STATUS = '"
					+ PMCConstant.TASK_COMPLETED + "' and p.COMPLETED_AT < '" + dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "'";
			System.err.println("get - " + query);
			q = this.getEntityManager().createNativeQuery(query.trim());
			resultList = q.executeUpdate();
			System.err.println("TASK_OWNERS archived Result : " + resultList.getClass() + " Result : " + resultList.toString());

		} catch (Exception e) {
			System.err.println("[PMC][ArchProcessTaskEventsDao][archiveData][Exception] : " + e.getClass().getSimpleName() + " [Cause: ]" + e.getMessage());
		}

	}

	/**
	 * @param Integer
	 *            noOfDays archived data need to be delete.
	 */
	public void deleteArchivedData(int noOfDays) {
		String query = "";
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss a");
		Query q = null;
		Object resultList = null;

		/**
		 * Erasing TASK_OWNERS Data archived data
		 */
		query = "delete from TASK_OWNERS where ROWID IN(select o.ROWID from TASK_OWNERS o, TASK_EVENTS t, PROCESS_EVENTS p where p.PROCESS_ID = t.PROCESS_ID and t.EVENT_ID= o.EVENT_ID and p.STATUS = '"
				+ PMCConstant.TASK_COMPLETED + "' and p.COMPLETED_AT < '" + dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "')";
		System.err.println("get - " + query);
		q = this.getEntityManager().createNativeQuery(query.trim());
		resultList = q.executeUpdate();
		System.err.println("TASK_OWNERS archived deleted data : " + resultList.getClass() + " Result : " + resultList.toString());

		/**
		 * Erasing TASK_EVENTS Data archived data
		 */
		query = "delete from TASK_EVENTS where ROWID IN(select t.ROWID from TASK_EVENTS t, PROCESS_EVENTS p where p.PROCESS_ID = t.PROCESS_ID and p.STATUS = '" + PMCConstant.TASK_COMPLETED
				+ "' and p.COMPLETED_AT < '" + dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "')";
		System.err.println("get - " + query);
		q = this.getEntityManager().createNativeQuery(query.trim());
		resultList = q.executeUpdate();
		System.err.println("TASK_EVENTS archived deleted data : " + resultList.getClass() + " Result : " + resultList.toString());

		/**
		 * Erasing PROCESS_EVENTS Data archived data
		 */
		query = "delete from PROCESS_EVENTS p where p.STATUS = '" + PMCConstant.TASK_COMPLETED + "' and p.COMPLETED_AT < '" + dateFormatter.format(ServicesUtil.getEarlierDate(noOfDays)) + "'";
		System.err.println("get - " + query);
		q = this.getEntityManager().createNativeQuery(query.trim());
		resultList = q.executeUpdate();
		System.err.println("PROCESS_EVENTS archived deleted data : " + resultList.getClass() + " Result : " + resultList.toString());

	}

	@Override
	protected ProcessEventsDo importDto(ProcessEventsDto fromDto) throws InvalidInputFault, ExecutionFault, NoResultFault {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProcessEventsDto exportDto(ProcessEventsDo entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
