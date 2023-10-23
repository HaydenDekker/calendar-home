# Calendar Home

Combine two Microsoft calendars into a single web UI.

Multiple user accounts can be connected to a single client that will consume calendar events, aggregating the calendar events to a single user interface.

## Functions / Tests

Redirect UseCase - 

- (Complete) Redirect user to Microsoft OAuth server.

Authorisation UseCase -

- (Complete) Receive Microsoft OAuth Authorisation codes.
- (Complete) Marshal Authorisation Response to Authorisation object.
- (Complete) Request User info and Tokens; Access and Refresh.
- (Complete) Saves user authorisation tokens
- (Complete) Detect failure if access token no longer valid.
- (Complete) Attempt refresh if failure occurs.

CalendarEventStream UseCase -

- (Complete) Can retrieve user calendar events from Microsoft graph.
- (Complete) Order events by date.
- Can listen for new events

UI Test

- (Complete) Display list of calendar events.
- Order in date order.
- Removes any duplication if the same event it pushed twice.
- (Complete) Display subject, description, date time start.
- Display notification if user account can be accessed.
- Allow user to un-subscribe from application.

CI/CD

- deploy/jenkins/dockerfile - adds java 17 to the jenkins image to allow maven to build this app
	need to run this manually on the server to create the image.
- deploy/jenkins/docker-compose.yaml - boots the jenkins image build manually above (need to set image name in this file) use docker-compose up to manually start this on the target server.
- deploy/calendar.service - Jenkins pipeline adds this to the server with each build to control the calendar service.
- deploy/setenv.sh - Jenkins pipline calls this each build to configure the server env with the new build.
- deploy/Jenkinsfile - File defining jenkins pipeline.

Deployment Environment Manual Tasks

- Add secrets to hosts /etc/environment file that is used by calendar.service
- APP_REDIRECT_URI_SIGNIN - redirection uri to be provided to auth server.
- APP_SECRET_KEY - secret key of auth server.



