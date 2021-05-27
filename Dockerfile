FROM airhacks/glassfish
COPY ./target/spotitube-back.war ${DEPLOYMENT_DIR}
