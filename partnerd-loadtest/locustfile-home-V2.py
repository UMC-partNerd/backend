from locust import task, FastHttpUser


class homedataV1(FastHttpUser):
    connection_timeout = 10.0
    network_timeout = 10.0

    @task
    def issue(self):
        with self.rest("GET", "/api/home/v2"):
            pass