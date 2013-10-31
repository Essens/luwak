package uk.co.flax.luwak;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.BytesRef;

/**
 * Copyright (c) 2013 Lemur Consulting Ltd.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class MonitorQuery {

    protected final String id;
    protected final Query query;
    protected final Query highlightQuery;

    protected final Presearcher presearcher;

    public MonitorQuery(String id, Query query, Query highlightQuery, Presearcher presearcher) {
        this.id = id;
        this.query = query;
        this.highlightQuery = highlightQuery;
        this.presearcher = presearcher;
    }

    public MonitorQuery(String id, Query query, Presearcher presearcher) {
        this(id, query, null, presearcher);
    }

    public String getId() {
        return id;
    }

    public final Document asIndexableDocument() {
        Document doc = new Document();
        presearcher.indexQuery(doc, query);
        doc.add(new StringField(Monitor.FIELDS.del_id, id, Field.Store.NO));
        doc.add(new SortedDocValuesField(Monitor.FIELDS.id, new BytesRef(id.getBytes())));
        return doc;
    }

    public final Query getDeletionQuery() {
        return new TermQuery(new Term(Monitor.FIELDS.del_id, id));
    }

    public Query getQuery() {
        return query;
    }

    public Query getHighlightQuery() {
        return highlightQuery;
    }

}
