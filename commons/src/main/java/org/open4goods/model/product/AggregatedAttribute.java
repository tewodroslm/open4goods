package org.open4goods.model.product;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.open4goods.model.attribute.AttributeType;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class AggregatedAttribute {

	/**
	 * The name of this aggregated attribute
	 */
	@Field(index = true, store = false, type = FieldType.Keyword)
	private String name;

	/**
	 * The value of this aggregated attribute
	 */
	@Field(index = true, store = false, type = FieldType.Keyword)	
	private String value;

	
	/** Type of the attribute **/
	@Field(index = false, store = false, type = FieldType.Keyword)
	private AttributeType type;
	
	/**
	 * Set to true if this aggregatedattribute is build from attributes having different values
	 */
	@Field(index = false, store = false, type = FieldType.Keyword)
	private boolean hasConflicts;
			
	
	/**
	 * The collections of conflicts for this attribute
	 */
	@Field(index = false, store = false, type = FieldType.Keyword)
	private Set<ConflictedAttribute> sources = new HashSet<>();
	

	
	// TODO : Simple, but does not allow to handle conflicts, and so on
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj instanceof AggregatedAttribute) {
			return name.equals(((AggregatedAttribute)obj).name);
		}
		return false;
	}
	
	
	
	
	/**
	 * 
	 * @return all attributes
	 */
	public Set<SourcedAttribute> sources() {
		return sources.stream().map(e -> e.getSources()).flatMap(e -> e.stream()) .collect(Collectors.toSet());
	}
	/**
	 * Add an attribute 
	 * @param parsed
	 */
	public void addAttribute(SourcedAttribute parsed) {

		ConflictedAttribute existing = sources.stream().filter(e -> e.getValue().equals(parsed.getRawValue().toString())).findAny().orElse(null);
		
		if (null == existing) {
			// No previous value with this attribute
			existing = new ConflictedAttribute();
			existing.setValue(parsed.getRawValue().toString());			
			sources.add(existing);
		} 
		
		// Updating the source 
		existing.getSources().add(parsed);
	}
	
	
	
	@Override
	public String toString() {
	
		return name + " : " + sources().size() + " source(s), " + (sources.size()-1) +" conflict(s)";
		

	}
	
	///////////////////////////////////////
	// Getters / Setters
	///////////////////////////////////////
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isHasConflicts() {
		return hasConflicts;
	}

	public void setHasConflicts(boolean hasConflicts) {
		this.hasConflicts = hasConflicts;
	}

	public Set<ConflictedAttribute> getSources() {
		return sources;
	}

	public void setSources(Set<ConflictedAttribute> sources) {
		this.sources = sources;
	}


	public AttributeType getType() {
		return type;
	}


	public void setType(AttributeType type) {
		this.type = type;
	}


	
	
	
	
	
	
	
	
	
}
