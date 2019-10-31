FROM postgres:alpine
HEALTHCHECK --start-period=3s --timeout=3s --retries=9 \
        CMD pg_isready -h 127.0.0.1 -p 5432 -d $$POSTGRES_DB -U $$POSTGRES_USER
ENV POSTGRES_DB=eemessenger
ENV POSTGRES_USER=eemessenger
ENV POSTGRES_PASSWORD=eemessenger
ENV POSTGRES_HOST=postgresql
ENV POSTGRES_PORT=5432

