#!/bin/bash

function createOrg3 {
	infoln "Enrolling the CA admin"
	mkdir -p ../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/

	export FABRIC_CA_CLIENT_HOME=${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/

  set -x
  fabric-ca-client enroll -u https://admin:adminpw@localhost:11054 --caname ca-org3 --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  echo 'NodeOUs:
  Enable: true
  ClientOUIdentifier:
    Certificate: cacerts/localhost-11054-ca-org3.pem
    OrganizationalUnitIdentifier: client
  PeerOUIdentifier:
    Certificate: cacerts/localhost-11054-ca-org3.pem
    OrganizationalUnitIdentifier: peer
  AdminOUIdentifier:
    Certificate: cacerts/localhost-11054-ca-org3.pem
    OrganizationalUnitIdentifier: admin
  OrdererOUIdentifier:
    Certificate: cacerts/localhost-11054-ca-org3.pem
    OrganizationalUnitIdentifier: orderer' > "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/config.yaml"

	infoln "Registering peer0"
  set -x
	fabric-ca-client register --caname ca-org3 --id.name peer0 --id.secret peer0pw --id.type peer --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering user"
  set -x
  fabric-ca-client register --caname ca-org3 --id.name user1 --id.secret user1pw --id.type client --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Registering the org admin"
  set -x
  fabric-ca-client register --caname ca-org3 --id.name org3admin --id.secret org3adminpw --id.type admin --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  infoln "Generating the peer0 msp"
  set -x
	fabric-ca-client enroll -u https://peer0:peer0pw@localhost:11054 --caname ca-org3 -M "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/msp" --csr.hosts peer0.org3.leadersofdigitalsvo.ru --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the peer0-tls certificates"
  set -x
  fabric-ca-client enroll -u https://peer0:peer0pw@localhost:11054 --caname ca-org3 -M "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls" --enrollment.profile tls --csr.hosts peer0.org3.leadersofdigitalsvo.ru --csr.hosts localhost --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null


  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/ca.crt"
  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/signcerts/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/server.crt"
  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/keystore/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/server.key"

  mkdir "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/tlscacerts"
  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/tlscacerts/ca.crt"

  mkdir "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/tlsca"
  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/tls/tlscacerts/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/tlsca/tlsca.org3.leadersofdigitalsvo.ru-cert.pem"

  mkdir "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/ca"
  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/peers/peer0.org3.leadersofdigitalsvo.ru/msp/cacerts/"* "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/ca/ca.org3.leadersofdigitalsvo.ru-cert.pem"

  infoln "Generating the user msp"
  set -x
	fabric-ca-client enroll -u https://user1:user1pw@localhost:11054 --caname ca-org3 -M "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/users/org3user1@org3.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/users/org3user1@org3.leadersofdigitalsvo.ru/msp/config.yaml"

  infoln "Generating the org admin msp"
  set -x
	fabric-ca-client enroll -u https://org3admin:org3adminpw@localhost:11054 --caname ca-org3 -M "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/users/org3admin@org3.leadersofdigitalsvo.ru/msp" --tls.certfiles "${PWD}/fabric-ca/org3/tls-cert.pem"
  { set +x; } 2>/dev/null

  cp "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/msp/config.yaml" "${PWD}/../organizations/peerOrganizations/org3.leadersofdigitalsvo.ru/users/org3admin@org3.leadersofdigitalsvo.ru/msp/config.yaml"
}
