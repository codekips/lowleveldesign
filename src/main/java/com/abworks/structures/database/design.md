## Base abstractions
1. Database
2. Table
3. Row
4. Schema
5. Index
6. Column

## Responsibilities
- Column: name, type, constraints
- Schema: Add, access column ordering
- Row: Has access to schema. Can insert data. check if a row satisfies some predicate.
- Table: Has list of rows. Has indexes.
- Index: lookup on values to rowset. ColumnIndex.
  - HashIndex (Value to rowset) map
  - RangeIndex (Value to rowset) sorted map
  - InvertedIndex (value.token to rowset) map
  
## Interfaces
- Table
  - addRow
  - updateRow(Map<String, Object>)
  - query(Query)
