#!/bin/bash

function createOrg1() {
  infoln "Enrolling the CA admin"
  mkdir -p organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/

  export FABRIC_CA_CLIENT_HOME=${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/

  set -x
  fabric-ca-client enroll -u https://admin:adminpw@localhost:7054 --caname ca-org1 --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  echo 'NodeOUs:
  Enable: true
  ClientOUIdentifier:
    Certificate: cacerts/localhost-7054-ca-org1.pem
    OrganizationalUnitIdentifier: client
  PeerOUIdentifier:
    Certificate: cacerts/localhost-7054-ca-org1.pem
    OrganizationalUnitIdentifier: peer
  AdminOUIdentifier:
    Certificate: cacerts/localhost-7054-ca-org1.pem
    OrganizationalUnitIdentifier: admin
  OrdererOUIdentifier:
    Certificate: cacerts/localhost-7054-ca-org1.pem
    OrganizationalUnitIdentifier: orderer' > "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Registering peer0"
  set -x
  fabric-ca-client register --caname ca-org1 --id.name peer0 --id.secret peer0pw --id.type peer --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering user"
  set -x
  fabric-ca-client register --caname ca-org1 --id.name user1 --id.secret user1pw --id.type client --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering the org admin"
  set -x
  fabric-ca-client register --caname ca-org1 --id.name org1admin --id.secret org1adminpw --id.type admin --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Generating the peer0 msp"
  set -x
  fabric-ca-client enroll -u https://peer0:peer0pw@localhost:7054 --caname ca-org1 -M "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/msp" --csr.hosts peer0.org1.leadersofdigitalsvo.ru --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the peer0-tls certificates"
  set -x
  fabric-ca-client enroll -u https://peer0:peer0pw@localhost:7054 --caname ca-org1 -M "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls" --enrollment.profile tls --csr.hosts peer0.org1.leadersofdigitalsvo.ru --csr.hosts localhost --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/ca.crt"
  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/signcerts/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/server.crt"
  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/keystore/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/server.key"

  mkdir -p "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/tlscacerts"
  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/tlscacerts/ca.crt"

  mkdir -p "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/tlsca"
  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/tlsca/tlsca.org1.leadersofdigitalsvo.ru-cert.pem"

  mkdir -p "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/ca"
  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/peers/peer0.org1.leadersofdigitalsvo.ru/msp/cacerts/"* "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/ca/ca.org1.leadersofdigitalsvo.ru-cert.pem"

  infoln "Generating the user msp"
  set -x
  fabric-ca-client enroll -u https://user1:user1pw@localhost:7054 --caname ca-org1 -M "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/users/org1user1@org1.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/users/org1user1@org1.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the org admin msp"
  set -x
  fabric-ca-client enroll -u https://org1admin:org1adminpw@localhost:7054 --caname ca-org1 -M "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/users/org1admin@org1.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/organizations/fabric-ca/org1/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org1.leadersofdigitalsvo.ru/users/org1admin@org1.leadersofdigitalsvo.ru/msp/config.yaml"
}

function createOrg2() {
  infoln "Enrolling the CA admin"
  mkdir -p organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/

  export FABRIC_CA_CLIENT_HOME=${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/

  set -x
  fabric-ca-client enroll -u https://admin:adminpw@localhost:8054 --caname ca-org2 --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  echo 'NodeOUs:
  Enable: true
  ClientOUIdentifier:
    Certificate: cacerts/localhost-8054-ca-org2.pem
    OrganizationalUnitIdentifier: client
  PeerOUIdentifier:
    Certificate: cacerts/localhost-8054-ca-org2.pem
    OrganizationalUnitIdentifier: peer
  AdminOUIdentifier:
    Certificate: cacerts/localhost-8054-ca-org2.pem
    OrganizationalUnitIdentifier: admin
  OrdererOUIdentifier:
    Certificate: cacerts/localhost-8054-ca-org2.pem
    OrganizationalUnitIdentifier: orderer' > "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Registering peer0"
  set -x
  fabric-ca-client register --caname ca-org2 --id.name peer0 --id.secret peer0pw --id.type peer --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering user"
  set -x
  fabric-ca-client register --caname ca-org2 --id.name user1 --id.secret user1pw --id.type client --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering the org admin"
  set -x
  fabric-ca-client register --caname ca-org2 --id.name org2admin --id.secret org2adminpw --id.type admin --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Generating the peer0 msp"
  set -x
  fabric-ca-client enroll -u https://peer0:peer0pw@localhost:8054 --caname ca-org2 -M "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/msp" --csr.hosts peer0.org2.leadersofdigitalsvo.ru --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the peer0-tls certificates"
  set -x
  fabric-ca-client enroll -u https://peer0:peer0pw@localhost:8054 --caname ca-org2 -M "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls" --enrollment.profile tls --csr.hosts peer0.org2.leadersofdigitalsvo.ru --csr.hosts localhost --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/ca.crt"
  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/signcerts/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/server.crt"
  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/keystore/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/server.key"

  mkdir -p "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/tlscacerts"
  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/tlscacerts/ca.crt"

  mkdir -p "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/tlsca"
  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/tlsca/tlsca.org2.leadersofdigitalsvo.ru-cert.pem"

  mkdir -p "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/ca"
  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/peers/peer0.org2.leadersofdigitalsvo.ru/msp/cacerts/"* "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/ca/ca.org2.leadersofdigitalsvo.ru-cert.pem"

  infoln "Generating the user msp"
  set -x
  fabric-ca-client enroll -u https://user1:user1pw@localhost:8054 --caname ca-org2 -M "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/users/org2user1@org2.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/users/org2user1@org2.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the org admin msp"
  set -x
  fabric-ca-client enroll -u https://org2admin:org2adminpw@localhost:8054 --caname ca-org2 -M "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/users/org2admin@org2.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/organizations/fabric-ca/org2/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/peerOrganizations/org2.leadersofdigitalsvo.ru/users/org2admin@org2.leadersofdigitalsvo.ru/msp/config.yaml"
}

function createOrderer() {
  infoln "Enrolling the CA admin"
  mkdir -p organizations/ordererOrganizations/leadersofdigitalsvo.ru

  export FABRIC_CA_CLIENT_HOME=${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru

  set -x
  fabric-ca-client enroll -u https://admin:adminpw@localhost:9054 --caname ca-orderer --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  echo 'NodeOUs:
  Enable: true
  ClientOUIdentifier:
    Certificate: cacerts/localhost-9054-ca-orderer.pem
    OrganizationalUnitIdentifier: client
  PeerOUIdentifier:
    Certificate: cacerts/localhost-9054-ca-orderer.pem
    OrganizationalUnitIdentifier: peer
  AdminOUIdentifier:
    Certificate: cacerts/localhost-9054-ca-orderer.pem
    OrganizationalUnitIdentifier: admin
  OrdererOUIdentifier:
    Certificate: cacerts/localhost-9054-ca-orderer.pem
    OrganizationalUnitIdentifier: orderer' > "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Registering orderer"
  set -x
  fabric-ca-client register --caname ca-orderer --id.name orderer --id.secret ordererpw --id.type orderer --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering the orderer admin"
  set -x
  fabric-ca-client register --caname ca-orderer --id.name ordererAdmin --id.secret ordererAdminpw --id.type admin --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Generating the orderer msp"
  set -x
  fabric-ca-client enroll -u https://orderer:ordererpw@localhost:9054 --caname ca-orderer -M "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/msp" --csr.hosts orderer.leadersofdigitalsvo.ru --csr.hosts localhost --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the orderer-tls certificates"
  set -x
  fabric-ca-client enroll -u https://orderer:ordererpw@localhost:9054 --caname ca-orderer -M "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls" --enrollment.profile tls --csr.hosts orderer.leadersofdigitalsvo.ru --csr.hosts localhost --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/ca.crt"
  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/signcerts/"* "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/server.crt"
  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/keystore/"* "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/server.key"

  mkdir -p "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/msp/tlscacerts"
  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/msp/tlscacerts/tlsca.leadersofdigitalsvo.ru-cert.pem"

  mkdir -p "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/msp/tlscacerts"
  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/orderers/orderer.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/msp/tlscacerts/tlsca.leadersofdigitalsvo.ru-cert.pem"

  infoln "Generating the admin msp"
  set -x
  fabric-ca-client enroll -u https://ordererAdmin:ordererAdminpw@localhost:9054 --caname ca-orderer -M "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/users/Admin@leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/organizations/fabric-ca/ordererOrg/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/organizations/ordererOrganizations/leadersofdigitalsvo.ru/users/Admin@leadersofdigitalsvo.ru/msp/config.yaml"
}
