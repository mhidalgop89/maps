package Util;

public class RelPerfilPermiso {
	
	private long relPerPrmId;
	private long perId;
	private long prmId;
	
	RelPerfilPermiso()
	{
		
	}
	
	
	RelPerfilPermiso(long relPerPrmId,long perId, long prmId )
	{
		this.relPerPrmId= relPerPrmId;
		this.perId= perId;
		this.prmId= prmId;
		
	}
	
	public long getRelPerPrmId() {
		return relPerPrmId;
	}
	public void setRelPerPrmId(long relPerPrmId) {
		this.relPerPrmId = relPerPrmId;
	}
	public long getPerId() {
		return perId;
	}
	public void setPerId(long perId) {
		this.perId = perId;
	}
	public long getPrmId() {
		return prmId;
	}
	public void setPrmId(long prmId) {
		this.prmId = prmId;
	}
	
	

}
