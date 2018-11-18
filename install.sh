#!/usr/bin/env bash



gcloud auth application-default login
gcloud config set project projet-sacc-si5

gsutil mb gs://projet-sacc-bucket
gsutil defacl set public-read gs://projet-sacc-bucket
