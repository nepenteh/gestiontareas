package com.ejerciciosmesa.tareas.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;


public class PageRender<T> {

	private String url;
	private Page<T> pageElements;	
	private int totalPages;	
	private int actualPage; 
	private int elementsPerPage;
	private int nSquares; 
	private List<PageItem> pageItems;   
	
	public PageRender(String url, Page<T> pageElements, int nSquares) {
		this.url = url;
		this.pageElements = pageElements;
		this.pageItems = new ArrayList<PageItem>();
		this.nSquares = nSquares;
		
		elementsPerPage = pageElements.getSize();
		totalPages = pageElements.getTotalPages();
		actualPage = pageElements.getNumber()+1;
		
		int from, to;
		
		if(totalPages <= nSquares) { 
			from = 1;
			to = totalPages;
		} else { 
			
			from = actualPage - (nSquares/2);
			to = actualPage + (nSquares/2);
			
			if(from<=0) {
				from = 1;
				to = nSquares;
			} else if(to>totalPages) {
				from = totalPages - nSquares + 1;
				to = totalPages;
			}
			
		}
		
		for(int i=from;i<=to;i++) {
			pageItems.add(new PageItem(i,i==actualPage));
		}
		
	}
	
	
	
	public String getUrl() {
		return url;
	}



	public Page<T> getPageElements() {
		return pageElements;
	}



	public int getTotalPages() {
		return totalPages;
	}



	public int getActualPage() {
		return actualPage;
	}



	public int getElementsPerPage() {
		return elementsPerPage;
	}



	public int getNSquares() {
		return nSquares;
	}



	public List<PageItem> getPageItems() {
		return pageItems;
	}



	public boolean isFirst() {
		return pageElements.isFirst();
	}
	
	public boolean isLast() {
		return pageElements.isLast();
	}
	
	public boolean isHasNext() {
		return pageElements.hasNext();
	}
	
	public boolean isHasPrevious() {
		return pageElements.hasPrevious();
	}
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

