import threading


class requestPut(threading.Thread):
	def __init__(self, url):
		threading.Thread.__init__(self)
		self.url = url
		return

	def run(self):
		return
