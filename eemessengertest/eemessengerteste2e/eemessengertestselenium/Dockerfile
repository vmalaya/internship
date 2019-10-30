FROM selenium/hub:3.141.59-vanadium
HEALTHCHECK --start-period=10s --timeout=2s --retries=99 \
                    CMD /opt/bin/check-grid.sh --host 0.0.0.0 --port 4444
