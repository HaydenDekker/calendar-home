# Calendar Home

Combine your Microsoft calendar with family or friends in a single web UI.

Aims to connect multiple user accounts to a single client and consume calendar events, aggregating the calendar events to a single user interface.

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

CalendarEventStream UseCase -

- (Complete) Can retrieve user calendar events from Microsoft graph.
- (Complete) Order events by date.
- Can listen for new events

UI Test

- (Complete) Display list of calendar events.
- Order in date order.
- Removes any duplication if the same event it pushed twice.
- Display, subject, description, date time start

