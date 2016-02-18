## LIST USERS ##

This command prints IDs of users with specified status (online, offline, all, playing, waiting)

**Arguments:**
  * status alias: online, offline, all, playing, waiting
  * secret key (returned by AUTHORIZE)

**Returns:**

SUCCESS, IDs of users with specified status in separate lines. Use PROFILE BY ID command to get user profile.

If command failed to retrieve the list, it returns FAIL.