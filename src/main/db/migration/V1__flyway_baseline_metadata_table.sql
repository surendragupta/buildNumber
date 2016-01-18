-- Table: schema_version

-- DROP TABLE schema_version;

CREATE TABLE schema_version
(
  version_rank integer NOT NULL,
  installed_rank integer NOT NULL,
  version character varying(50) NOT NULL,
  description character varying(200) NOT NULL,
  type character varying(20) NOT NULL,
  script character varying(1000) NOT NULL,
  checksum integer,
  installed_by character varying(100) NOT NULL,
  installed_on timestamp without time zone NOT NULL DEFAULT now(),
  execution_time integer NOT NULL,
  success boolean NOT NULL,
  CONSTRAINT schema_version_pk PRIMARY KEY (version)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schema_version
  OWNER TO postgres;

-- Index: schema_version_ir_idx

-- DROP INDEX schema_version_ir_idx;

CREATE INDEX schema_version_ir_idx
  ON schema_version
  USING btree
  (installed_rank);

-- Index: schema_version_s_idx

-- DROP INDEX schema_version_s_idx;

CREATE INDEX schema_version_s_idx
  ON schema_version
  USING btree
  (success);

-- Index: schema_version_vr_idx

-- DROP INDEX schema_version_vr_idx;

CREATE INDEX schema_version_vr_idx
  ON schema_version
  USING btree
  (version_rank);

