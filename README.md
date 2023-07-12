# Calendar Home

Aims to connect many user accounts to a single client and consumer calendar events aggregating to a single display.

## Functions / Tests

SecurityTest - 

- (Complete) Redirect user to Microsoft OAuth server.
- (Complete) Receive Microsoft OAuth Authorisation codes.
- (Complete) Marshal Authorisation Response to Authorisation object.
- (Complete) Request Tokens; Credentials and Refresh.
- Store Refresh token.
- Set access tokens that app should utilise.
- Detect failure if access token no longer valid.
- Get refresh if failure occurs.
- Call with refresh is failure occurs.

OutlookMonitorTest -

- Can retrieve user info from Microsoft graph.
- Can retrieve user calendar events from Microsoft graph.

UITest

- Display list of calendar events in date order.

