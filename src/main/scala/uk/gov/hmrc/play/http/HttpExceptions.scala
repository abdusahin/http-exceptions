/*
 * Copyright 2014 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.hmrc.play.http

import HttpExceptions._

private object HttpExceptions {
	val UNAUTHORIZED = 401
	val BAD_REQUEST = 400
	val FORBIDDEN = 403
	val NOT_FOUND = 404
	val PRECONDITION_FAILED = 412
	val REQUEST_ENTITY_TOO_LARGE = 413
	val BAD_GATEWAY = 502
	val GATEWAY_TIMEOUT = 504
}

class HttpException(val message: String, val responseCode: Int) extends Exception(message)

class UnauthorizedException(message: String) extends HttpException(message, UNAUTHORIZED)

class BadRequestException(message: String) extends HttpException(message, BAD_REQUEST)

class ForbiddenException(message: String) extends HttpException(message, FORBIDDEN)

class NotFoundException(message: String) extends HttpException(message, NOT_FOUND)

class PreconditionFailedException(message: String) extends HttpException(message, PRECONDITION_FAILED)

class RequestEntityTooLargeException(message: String) extends HttpException(message, REQUEST_ENTITY_TOO_LARGE)

class BadGatewayException(message: String) extends HttpException(message, BAD_GATEWAY)

class GatewayTimeout(message: String) extends HttpException(message, GATEWAY_TIMEOUT)

case class Upstream4xxResponse(message: String, upstreamResponseCode: Int, reportAs: Int) extends Exception(message)

case class Upstream5xxResponse(message: String, upstreamResponseCode: Int, reportAs: Int) extends Exception(message)