DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'flyway') THEN
      CREATE USER flyway WITH PASSWORD 'flyway_password';
END IF;

   GRANT ALL PRIVILEGES ON DATABASE tasks_database TO flyway;

   GRANT USAGE ON SCHEMA public TO flyway;
   GRANT CREATE ON SCHEMA public TO flyway;

   ALTER SCHEMA public OWNER TO flyway;
END $$;