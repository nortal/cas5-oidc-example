# An example of CAS5 and CAS5 management app setup with OIDC

It also supports custom client properties to be issued as claims for
access tokens obtained via client_credentials.

## Development
1. Build with `gradle war`.
2. Use docker-compose bundle at `/docker/castom`:
```bash
$ cd /docker/castom
$ docker-compose up
```
3. Add `cas` entry for cas-container to `/etc/hosts`, e.g.:
```bash
$ docker-inspect castom_cas_1 | grep IPAddress
"SecondaryIPAddresses": null,
"IPAddress": "",
        "IPAddress": "172.21.0.3",
$ echo "172.21.0.3 cas" >> /etc/hosts
```
You may need to change this entry after every `docker-compose up`.
4. Login to management app at `http://localhost:8445/cas-management` using
default credentials `casuser/Mellon`.
5. Add an OIDC client:
  1. Redirect uri can be arbitrary.
  2. Use a single space as jwks uri.
  3. Encryption algorithms do not matter.
  4. Add your extra claim into as a property. It will be seen in userinfo url (cas/oidc/profile).
  5. If you want ID-tokens to contain the claim, add it to `cas.authn.oidc.claims` in `application.yml`, e.g.:
  ```
  cas.authn.oidc.claims: org,sub,name,preferred_username,family_name
  ```
  6. Try obtaining tokens via client_credentials grant. See https://tools.ietf.org/html/rfc6749#section-4.4.
  7. You can introspect tokens via https://tools.ietf.org/html/rfc7662#section-2
  and read userinfo via http://openid.net/specs/openid-connect-core-1_0.html#UserInfo
  (note that http://cas:8080/cas/oidc/.well-known defines userinfo endpoint to be at cas/oidc/profile).
  8. Debug id-token claims using https://jwt.io/ .

## Server deployments
See `docker/prodhttpd` for an example with dockerized apache server and `docker/prod` for an example that would use an external apache server.

1. Copy both wars to `wars` subfolder.
2. `docker-compose up -d`

External apache conf sample (excerpt):
```
ProxyPass             /cas ajp://localhost:8209/cas
ProxyPassReverse      /cas ajp://localhost:8209/cas

ProxyPass             /cas-management ajp://localhost:8219/cas-management
ProxyPassReverse      /cas-management ajp://localhost:8219/cas-management
```
## Relevant CAS docs
* https://apereo.github.io/cas/development/installation/OIDC-Authentication.html
* https://apereo.github.io/cas/development/installation/Service-Management.html
* https://apereo.github.io/cas/development/installation/JPA-Service-Management.html
