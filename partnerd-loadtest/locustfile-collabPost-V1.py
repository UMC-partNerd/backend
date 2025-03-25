from locust import task, FastHttpUser


class homedataV1(FastHttpUser):
    connection_timeout = 10.0
    network_timeout = 10.0

    @task
    def issue(self):
        payload = {
            "sortBy" : "endDate",
            "size" : 9,
            "pageNum" : 1,
        }
        with self.rest("GET", "/api/collabPosts", json=payload):
            pass