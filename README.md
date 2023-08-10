# Calendar Home

Aims to connect many user accounts to a single client and consumer calendar events aggregating to a single display.

## Functions / Tests

Redirect UseCase - 

- (Complete) Redirect user to Microsoft OAuth server.

Authorisation UseCase -

- (Complete) Receive Microsoft OAuth Authorisation codes.
- (Complete) Marshal Authorisation Response to Authorisation object.
- (Complete) Request User info and Tokens; Access and Refresh.
- (Complete) Saves user authorisation tokens
- Detect failure if access token no longer valid.
- Attempt refresh if failure occurs.

OutlookMonitor UseCase -

- Can retrieve user info from Microsoft graph.
- (Complete) Can retrieve user calendar events from Microsoft graph.
- (Complete) Order events by date.

UI Test

- Display list of calendar events in date order.

