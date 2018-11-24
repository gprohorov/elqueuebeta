package com.med.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Created by george on 22.11.18.
 */
@Document
public class OutcomeTree {
    @Id
    private String id;
    private LocalDateTime createTime = LocalDateTime.now();
    private String name;
    
    @Nullable
    private String catID;
    
    @Transient
    private long sum;
    
    @Transient
    private List<OutcomeTree> items;
    
    public OutcomeTree() { }

    public OutcomeTree(String name, String catID) {
        this.name = name;
        this.catID = catID;
    }
    
    public OutcomeTree(String name, String catID, long sum) {
    	this.name = name;
    	this.catID = catID;
    	this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getCreateTime() {
        return createTime;
    }
	
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public List<OutcomeTree> getItems() {
		return items;
	}

	public void setItems(List<OutcomeTree> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "OutcomeTree [id=" + id + ", createTime=" + createTime + ", name=" + name + ", catID=" + catID + "]";
	}
}
