package org.open4goods.model.attribute;

import org.open4goods.exceptions.InvalidParameterException;
import org.open4goods.model.data.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Contains min / max / average /sum informations for a rating
 *
 * @author goulven
 *
 */
public class Cardinality {

	protected static final Logger logger = LoggerFactory.getLogger(Cardinality.class);

	
	@Field(index = true, store = false, type = FieldType.Double)
	private Double value;
	
	/**
	 * The minimum encountered
	 */
	@Field(index = true, store = false, type = FieldType.Double)
	private Double min = null;

	/**
	 * The maximum encountered
	 */
	@Field(index = true, store = false, type = FieldType.Double)		
	private Double max = null;

	/**
	 * The average value
	 */
	@Field(index = true, store = false, type = FieldType.Double)	
	private Double avg = null;

	/**
	 * The number of elements for computing this cardinality
	 */
	@Field(index = true, store = false, type = FieldType.Double)	
	private Integer count = 0;

	/**
	 * The sum
	 */
	@Field(index = true, store = false, type = FieldType.Double)		
	private Double sum = 0.0;



	public Cardinality() {
	}




	public Cardinality(Cardinality source) {
		
		this.avg = source.avg;
		this.count=source.count;
		this.max=source.max;
		this.min=source.min;
		this.sum=source.sum;
		this.value=source.value;
	}




	@Override
	public String toString() {
		return min +" > " + avg + " < " +max+" (" + count+ " elements)";
	}



	
	
	public void increment(final Rating r) {

		try {
			// Min
			if (null == min || min > r.getValue()) {
				min = r.getValue();
			}

			// Max
			if (null == max || max < r.getValue()) {
				max = r.getValue();
			}

			// Count
			count++;

			// Sum
			sum += r.getValue();

			// Average
			avg = sum / Double.valueOf(count);

		} catch (Exception e) {
			logger.error("Cardinality computation failed : {}", e.getMessage());
		}
	}
		
	/**
	 * Increments rating cardinality with a
	 *
	 * @param r
	 * @throws InvalidParameterException
	 */
	public void increment(Double value) {

		try {
			// Min
			if (null == min || min > value) {
				min = value;
			}

			// Max
			if (null == max || max < value) {
				max = value;
			}

			// Count
			count++;

			// Sum
			sum += value;

			// Average
			avg = sum / Double.valueOf(count);

		} catch (Exception e) {
			logger.error("Cardinality computation failed : {}", e.getMessage());
		}
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}


	public Double getValue() {
		return value;
	}




	public void setValue(Double value) {
		this.value = value;
	}
	
	

}