/*
 * Copyright 2020 Precog Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fs2
package job

import scala.{Boolean, Left, Right}
import scala.util.Either

final case class Job[F[_], I, N, R](id: I, run: Stream[F, Either[N, R]]) {
  def filterNotifications(p: N => Boolean): Job[F, I, N, R] =
    Job(id, run.filter {
      case Left(n) => p(n)
      case Right(_) => true
    })

  def silent: Job[F, I, N, R] =
    filterNotifications(_ => false)
}
