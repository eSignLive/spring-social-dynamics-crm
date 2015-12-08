package org.springframework.social.dynamicscrm.api.domain.odata;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by psmelser on 2015-12-04.
 *
 * @author paul_smelser@silanis.com
 */
public class ODataQuery {
    private String select;
    private String filter;
    private String orderBy;
    private int skip;
    private int top;

    private Queue<String> queryElements = new LinkedList<String>();
    private Iterator<String> queryIterator;

    public String next(){
        if (queryIterator == null) queryIterator = queryElements.iterator();
        return queryIterator.hasNext() ? queryIterator.next() : null;
    }

    public void reset(){
        queryIterator = null;
    }

    public String getSelect() {
        return select;
    }

    public ODataQuery withSelect(String select) {
        this.select = select;
        queryElements.add("$select=" + select);
        return this;
    }

    public String getFilter() {
        return filter;
    }


    public ODataQuery withFilter(String filter) {
        this.filter = filter;
        queryElements.add("$filter=" + filter);
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public ODataQuery orderBy(String orderBy) {
        this.orderBy = orderBy;
        queryElements.add("$orderBy="+orderBy);
        return this;
    }

    public int getSkip() {
        return skip;
    }

    public ODataQuery skip(int skip) {
        this.skip = skip;
        queryElements.add("$skip="+String.valueOf(skip));
        return this;
    }

    public int getTop() {
        return top;
    }

    public ODataQuery top(int top) {
        this.top = top;
        queryElements.add("$top="+String.valueOf(top));
        return this;
    }

    public boolean any(){
        return any(select, filter, orderBy, String.valueOf(skip), String.valueOf(top));
    }
    private boolean any(String ... queries){
        boolean result = false;
        for(String query : queries){
            if (query != null)
                result = true;
        }
        return result;
    }
}
